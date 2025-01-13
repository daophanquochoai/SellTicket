package doctorhoai.learn.user_service.helper;

import doctorhoai.learn.user_service.dto.AccountDto;
import doctorhoai.learn.user_service.dto.RoleDto;
import doctorhoai.learn.user_service.entity.Account;
import doctorhoai.learn.user_service.entity.Role;
import doctorhoai.learn.user_service.entity.Status;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MapperToDtoTest {

    @Test
    public void Test_DtoToAccount(){
        Role role = new Role("ADMIN", Status.ACTIVE);
        Account account = new Account("quochoai","100303",role,Status.ACTIVE);
        AccountDto accountDto = MapperToDto.AccountToDto(account);

        RoleDto roleDto = MapperToDto.RoleToDto(role);
        AccountDto accountDto1 = MapperToDto.AccountToDto(account);

        assertEquals(accountDto1,account);
    }

}