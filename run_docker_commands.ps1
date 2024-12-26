if (docker ps -a --filter "name=mysql_hometask_5and6" --format "{{.Names}}" | Select-String "mysql_hometask_5and6") {
    Write-Host "Stopping and removing existing container..."
    docker stop mysql_hometask_5and6 | Out-Null
    docker rm mysql_hometask_5and6 | Out-Null
}

Write-Host "Starting a new MySQL container..."
docker run -d --name mysql_hometask_5and6 `
    -e MYSQL_ROOT_PASSWORD=11111 `
    -e MYSQL_DATABASE=db `
    -e MYSQL_USER=user1 `
    -e MYSQL_PASSWORD=password1 `
    -p 3311:3306 mysql:latest

Write-Host "Waiting for MySQL container to initialize..."
Start-Sleep -Seconds 3

Write-Host "Checking container status..."
docker ps --filter "name=mysql_hometask_5and6" --format "table {{.Names}}\t{{.Status}}"

Write-Host "Copying db_init.sql into the container..."
docker cp .\db_init.sql mysql_hometask_5and6:/db_init.sql
Write-Host "Waiting for MySQL container to initialize..."
Start-Sleep -Seconds 3

# Выполняем SQL файл, который н евыполнится и надо войти в cmd руками и потом выполнить команду
Write-Host "Executing SQL script inside the container..."
cmd /c "docker exec -i mysql_hometask_5and6 mysql -u root -p11111<.\db_init.sql"

