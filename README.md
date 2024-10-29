# AuthService

AuthService là một ứng dụng dựa trên Spring Boot để quản lý xác thực và thông tin người dùng. Nó cung cấp API REST để hỗ trợ đăng nhập, quản lý vai trò và mật khẩu người dùng, cũng như quản lý các nhân viên.

## Yêu cầu hệ thống
- Java 17
- PostgreSQL (hoặc cơ sở dữ liệu khác với cấu hình tương tự)
- Maven hoặc Gradle

## Các thư viện chính
- `Spring Boot Web`: Để xây dựng các API RESTful.
- `Spring Boot Data JPA`: Để thao tác cơ sở dữ liệu.
- `PostgreSQL Driver`: Để kết nối với PostgreSQL.
- `Spring Boot Security`: Để bảo mật các điểm cuối API.
- `Spring Boot OAuth2`: Để hỗ trợ xác thực OAuth2.
- `Lombok`: Giảm mã lặp bằng cách tạo tự động các getter, setter, constructor, v.v.
- `Nimbus JOSE JWT` và `JJWT`: Để xử lý và xác thực JSON Web Tokens (JWT).
- `Springdoc OpenAPI`: Để tạo tài liệu API tự động.

## Cài đặt
1. Clone kho lưu trữ này về máy:
    ```bash
    git clone https://github.com/huyvulccd/ppmem-auth_service.git
    ```

2. Cấu hình PostgreSQL trong `application.properties`:
    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/your_database
    spring.datasource.username=your_username
    spring.datasource.password=your_password
    ```

3. Chạy ứng dụng:
    ```bash
    ./mvnw spring-boot:run
    ```

## API Endpoints

### AuthController (Xác thực người dùng)

- **POST `/api/auth/login`**: Đăng nhập người dùng.
  - Yêu cầu: `LoginRequest`
  - Phản hồi: `TokenJwtResponse`
  - Mã lỗi: 401 Unauthorized nếu thông tin không hợp lệ

- **POST `/api/auth/introspection`**: Kiểm tra trạng thái của token.
  - Yêu cầu: `Authorization` header (AccessToken), `RefreshToken`
  - Phản hồi: `TokenJwtResponse`

- **POST `/api/auth/logout`**: Đăng xuất người dùng.
  - Yêu cầu: `Authorization` header (AccessToken)
  - Phản hồi: Xóa session người dùng khỏi server, trả về trạng thái đăng xuất thành công.

- **POST `/api/auth/forgot-password`**: Yêu cầu reset mật khẩu qua email.
  - Yêu cầu: Email người dùng
  - Phản hồi: Thông báo gửi email đặt lại mật khẩu.

- **POST `/api/auth/reset-password`**: Đặt lại mật khẩu.
  - Yêu cầu: `ResetPasswordRequest`
  - Phản hồi: Thông báo đặt lại mật khẩu thành công.

### EmployeeController (Quản lý nhân viên)

- **POST `/api/management-employees/register`**: Đăng ký nhân viên mới.
  - Yêu cầu: `RegisterRequest`
  - Phản hồi: Xác nhận đăng ký thành công, hoặc lỗi 400 nếu dữ liệu không hợp lệ.

- **POST `/api/management-employees/assign-roleUser`**: Gán vai trò cho người dùng.
  - Yêu cầu: `Authorization` header (AccessToken), `role`
  - Phản hồi: Thông báo phân quyền thành công.

- **GET `/api/management-employees`**: Lấy thông tin tất cả nhân viên.
  - Phản hồi: Danh sách nhân viên.

- **GET `/api/management-employees/{id}`**: Lấy thông tin chi tiết nhân viên theo ID.
  - Yêu cầu: `id` (ID nhân viên)
  - Phản hồi: Thông tin chi tiết nhân viên.

- **DELETE `/api/management-employees/{id}`**: Xóa nhân viên theo ID.
  - Yêu cầu: `id` (ID nhân viên)
  - Phản hồi: Thông báo xóa thành công.

## Cấu trúc dữ liệu

### Yêu cầu đầu vào
- `LoginRequest`: Chứa thông tin đăng nhập của người dùng (username, password).
- `ResetPasswordRequest`: Chứa thông tin mật khẩu mới.
- `RegisterRequest`: Chứa thông tin đăng ký của nhân viên.

### Phản hồi
- `TokenJwtResponse`: Chứa access token, refresh token và thời gian hết hạn của token.
  
## Đóng góp
Nếu bạn muốn đóng góp, vui lòng tạo một nhánh (`git checkout -b feature/AmazingFeature`) và thực hiện pull request.

## Tác giả
Nguyễn Huy Vũ - [huyvulccd](https://github.com/huyvulccd)

## License
Dự án này sử dụng giấy phép MIT.
