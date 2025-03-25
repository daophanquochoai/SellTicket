# 🎬 Hệ Thống Bán Vé Xem Phim

## 📌 Giới Thiệu
Hệ thống bán vé xem phim trực tuyến được xây dựng dựa trên kiến trúc microservices, giúp tối ưu hóa khả năng mở rộng và bảo trì. Dự án sử dụng công nghệ **Spring Boot, Spring Cloud, Eureka, Redis, MySQL** ở backend và **React** ở frontend. Hệ thống đã được container hóa với Docker và quản lý dịch vụ bằng **docker-compose**.

---

## ⚙️ Chức Năng
### 1️⃣ Người Dùng
- Đăng ký, đăng nhập, xác thực người dùng.
- Xem danh sách phim theo lịch chiếu.
- Chọn ghế, đặt vé và thanh toán trực tuyến.
- Quản lý thông tin cá nhân và lịch sử đặt vé.

### 2️⃣ Quản Lý
- Quản lý danh sách phim, lịch chiếu.
- Quản lý phòng chiếu và sơ đồ ghế ngồi.
- Quản lý đồ ăn bán.
- Custom danh sách hiển thị.
- Theo dõi hóa đơn đặt.
- Quản lý tài khoản người dùng và nhân viên.
- Thống kê, báo cáo doanh thu.

---

## 🏗️ Kiến Trúc Hệ Thống
Hệ thống được chia thành các microservices độc lập, giao tiếp với nhau thông qua API Gateway:
- **Proxy**: Xác thực và phân quyền.
- **Api Gateway**: Quản lý các dịch vụ gọi đến, cân bằng tải.
- **Config Service**: Tạo noi lưu trữ chung các file cấu hình
- **Payment Service**: Xử lý thanh toán, vé.
- **Dish Service**: Quản lí về đồ ăn.
- **Eureka Service**: Quản lí domain, port của các service.
- **Film Service**: Quản lí về các bộ phim.
- **Notification Service**: Quản lí về thông báo, upload ảnh, email.
- **Rate Service**: Quản lí về đánh giá phim.
- **Room Service**: Quản lí về phòng chiếu.
- **Showtime Service**: Quản lí về thời gian chiếu.
- **User Service**: Quản lí về tài khoản.

Các dịch vụ được đăng ký và quản lý thông qua **Eureka Service Discovery**. Bộ nhớ cache **Redis** được sử dụng để tối ưu hiệu suất. Dữ liệu được lưu trữ trong **MySQL**.

---

## 🛠️ Công Nghệ Sử Dụng
### Backend
- **Spring Boot**: Framework chính cho các microservices.
- **Spring Cloud**: Quản lý kiến trúc microservices.
- **Spring Security & JWT**: Xác thực và bảo mật.
- **Eureka Server**: Service discovery.
- **Redis**: Lưu trữ cache, tối ưu hiệu suất.
- **MySQL**: Hệ quản trị cơ sở dữ liệu.
- **Zipkin**: Theo dõi hoạt động của các service
- **Docker & Docker Compose**: Đóng gói và triển khai dịch vụ.
- **Kafka**: Giúp chống trôi khi message gửi.

### Frontend
- **ReactJS**: Giao diện người dùng.
- **Axios**: Giao tiếp với API.
- **Tailwind**: Giúp tối ưu hóa css giao diện.
- **Antd**: Tao giao diện cách chuyên nghiêp.

---

## 🚀 Triển Khai Với Docker
### 1️⃣ Cài Đặt Docker
Yêu cầu hệ thống:
- **Docker** >= 20.x
- **Docker Compose** >= 1.29

### 2️⃣ Build & Chạy Dự Án
Di chuyển đến thư mục chứa `docker-compose.yaml` và chạy:
```bash
cd docker
docker-compose up --build -d
```
Dự án sẽ tự động tải các image, build container và khởi chạy toàn bộ hệ thống.

### 3️⃣ Kiểm Tra Trạng Thái
Để kiểm tra trạng thái các container:
```bash
docker ps
```

---

## 📧 Liên Hệ
- **Tác giả**: Đào Phan Quốc Hoài
- **Email**: dpquochoai@gmail.com

🚀 Cảm ơn bạn đã quan tâm đến dự án của chúng tôi! 🎉

