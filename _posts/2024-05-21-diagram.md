<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Hall Pass Management</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }
        .container {
            width: 80%;
            margin: 20px auto;
            background: #fff;
            padding: 20px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h1 {
            text-align: center;
        }
        form {
            margin-bottom: 20px;
        }
        input, select {
            width: calc(50% - 20px);
            padding: 10px;
            margin: 10px;
        }
        button {
            padding: 10px 20px;
            margin: 10px;
            cursor: pointer;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        table, th, td {
            border: 1px solid #ddd;
        }
        th, td {
            padding: 10px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        .actions {
            display: flex;
            justify-content: center;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Hall Pass Management</h1>
        <form id="hallPassForm">
            <input type="text" id="studentId" placeholder="Student ID" required>
            <input type="text" id="teacherId" placeholder="Teacher ID" required>
            <input type="text" id="reason" placeholder="Reason" required>
            <input type="datetime-local" id="expiryTime" required>
            <button type="submit">Create Hall Pass</button>
        </form>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Student ID</th>
                    <th>Teacher ID</th>
                    <th>Reason</th>
                    <th>Expiry Time</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody id="hallPassTableBody">
                <!-- Hall Pass records will be injected here by JavaScript -->
            </tbody>
        </table>
    </div>

    <script>
        const apiUrl = 'http://172.27.30.9:8091/api/hallpass';

        document.addEventListener('DOMContentLoaded', () => {
            fetchHallPasses();

            document.getElementById('hallPassForm').addEventListener('submit', async (event) => {
                event.preventDefault();
                const hallPass = {
                    studentId: document.getElementById('studentId').value,
                    teacherId: document.getElementById('teacherId').value,
                    reason: document.getElementById('reason').value,
                    expiryTime: document.getElementById('expiryTime').value
                };
                await createHallPass(hallPass);
                fetchHallPasses();
            });
        });

        async function fetchHallPasses() {
            const response = await fetch(apiUrl);
            const hallPasses = await response.json();
            const tableBody = document.getElementById('hallPassTableBody');
            tableBody.innerHTML = '';
            hallPasses.forEach(hallPass => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${hallPass.id}</td>
                    <td>${hallPass.studentId}</td>
                    <td>${hallPass.teacherId}</td>
                    <td>${hallPass.reason}</td>
                    <td>${hallPass.expiryTime}</td>
                    <td class="actions">
                        <button onclick="deleteHallPass(${hallPass.id})">Delete</button>
                    </td>
                `;
                tableBody.appendChild(row);
            });
        }

        async function createHallPass(hallPass) {
            await fetch(apiUrl, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(hallPass)
            });
        }

        async function deleteHallPass(id) {
            await fetch(`${apiUrl}/${id}`, { method: 'DELETE' });
            fetchHallPasses();
        }
    </script>
</body>
</html>
