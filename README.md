---

# CHUẨN BỊ TRƯỚC KHI CHẠY CÁC SERVICE

## Mở Project trong Eclipse

Có thể mở các thư mục service này trong ứng dụng **Eclipse** theo hai cách:

1.  **Trực tiếp mở các folder này trong Eclipse.**
2.  **Nếu muốn thêm các folder này vào các workspace khác:**
    * Vào `File` -> `Import` -> `Existing Maven Project`.
    * Chọn các thư mục service cần thêm.
    * Nhấp `Finish`.

---

## Chuẩn bị RabbitMQ

Trước khi chạy các service, cần đảm bảo **RabbitMQ** đã được khởi động. Thực hiện theo các bước sau:

1.  **Truy cập đường dẫn chứa file server của RabbitMQ:**
    `C:\Program Files\Erlang OTP\lib\rabbitmq_server-4.1.0\sbin`
    *(Lưu ý: Đường dẫn có thể thay đổi tùy thuộc vào phiên bản và vị trí cài đặt của bạn.)*

2.  **Mở cửa sổ Command Prompt (CMD) tại đường dẫn trên** và nhập lệnh sau:
    ```bash
    rabbitmq-server.bat
    ```

3.  **Mở thêm một cửa sổ CMD thứ hai** (cũng tại đường dẫn trên) và nhập lệnh để kích hoạt plugin quản lý RabbitMQ:
    ```bash
    rabbitmq-plugins enable rabbitmq_management -online
    ```

4.  **Truy cập giao diện quản lý RabbitMQ** qua trình duyệt web:
    [http://localhost:15672/](http://localhost:15672/)
    Bạn sẽ cần đăng nhập để kiểm tra các queue khi chạy các service. Thông thường, tên đăng nhập và mật khẩu mặc định là `guest/guest`.

---

# CHẠY TOÀN BỘ SERVICE

Sau khi đã chuẩn bị RabbitMQ, có thể chạy các service theo trình tự sau:

1.  Trong **Eclipse**, nhấp chuột phải vào từng service bạn muốn chạy.
2.  Chọn `Run As` -> `Spring Boot App`.

Thực hiện lần lượt cho tất cả các service cần thiết để khởi động hệ thống.