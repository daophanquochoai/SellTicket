package doctorhoai.learn.notificationservice.controller;

import com.google.gson.Gson;
import doctorhoai.learn.basedomain.Event.BillChairTicket;
import doctorhoai.learn.basedomain.Event.TicketEmail;
import doctorhoai.learn.notificationservice.service.inter.CloudinaryService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping
@CrossOrigin("*")
public class Controller {

    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();
    private final CloudinaryService cloudinaryService;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @PostConstruct
    public void init() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            executor.shutdown();
            try {
                executor.awaitTermination(1, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                log.error(e.toString());
            }
        }));
    }

    @GetMapping("/sse/subscribe")
    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter(10 * 60 * 1000L); // Timeout 10 phút
        emitters.add(emitter);

        emitter.onCompletion(() -> {
            log.info("Client đóng kết nối, xóa emitter");
            emitters.remove(emitter);
        });

        emitter.onTimeout(() -> {
            log.info("SSE timeout, đóng emitter");
            emitters.remove(emitter);
            emitter.complete();
        });

        return emitter;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("image") MultipartFile file) throws IOException {
        return ResponseEntity.ok().body(
                cloudinaryService.uploadImage(file)
        );
    }


    @Async("taskExecutor")
    public void sendSSE(List<BillChairTicket> billChairTicket) {
        for (SseEmitter emitter : new ArrayList<>(emitters)) { // Tránh lỗi ConcurrentModificationException
            executor.execute(() -> {
                try {
                    System.out.println("send chair....");
                    emitter.send(SseEmitter.event()
                            .name("ticketEmail")
                            .data(billChairTicket, MediaType.APPLICATION_JSON));

                } catch (IOException e) {
                    log.error("SSE gửi lỗi: " + e.getMessage());
                    emitters.remove(emitter);
                    emitter.completeWithError(e);
                }
            });
        }
    }


}
