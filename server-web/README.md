# Use case

dev:

    ```sh
    go run main.go -a http://localhost:8030 -m http://localhost:8060 -u http://localhost:8061
    ```

prod:

    ```sh
    go build
    ./cyberbrick-web-server -a http://localhost:8030 -m http://localhost:8060 -u http://localhost:8061
    ```
