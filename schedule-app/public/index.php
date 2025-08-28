<?php
$host = getenv('DB_HOST') ?: 'db';
$dbname = 'db01'; // または getenv('DB_NAME')
$user = getenv('DB_USER') ?: 'app';
$pass = getenv('DB_PASS') ?: 'apppass';

try {
    $pdo = new PDO("mysql:host=$host;dbname=$dbname;charset=utf8mb4", $user, $pass);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    echo "<h1>Users Table:</h1>";
    $stmt = $pdo->query("SELECT * FROM users");

    echo "<ul>";
    while ($row = $stmt->fetch(PDO::FETCH_ASSOC)) {
        echo "<li>ID: {$row['id']}, Name: {$row['name']}</li>";
    }
    echo "</ul>";
} catch (PDOException $e) {
    echo "Connection failed: " . $e->getMessage();
}
?>
