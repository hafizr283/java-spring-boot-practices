<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Result</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body {
            font-family: 'Segoe UI', Arial, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .card {
            background: #ffffff;
            padding: 50px 70px;
            border-radius: 20px;
            box-shadow: 0 20px 40px rgba(0, 0, 0, 0.3);
            text-align: center;
        }
        .question {
            font-size: 22px;
            color: #888;
            margin-bottom: 10px;
        }
        .result {
            font-size: 72px;
            font-weight: bold;
            color: #667eea;
        }
    </style>
</head>
<body>
    <div class="card">
        <p class="question">The sum is</p>
        <p class="result">${sum}</p>
    </div>
</body>
</html>
