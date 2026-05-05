import { useEffect, useState } from "react";
import API from "../services/api";

function Dashboard() {
  const [data, setData] = useState([]);

  useEffect(() => {
    API.get("/products")
      .then(res => setData(res.data))
      .catch(() => alert("Unauthorized"));
  }, []);

  return (
    <div>
      <h2>Dashboard</h2>

      {data.map((item) => (
        <p key={item.id}>{item.name}</p>
      ))}
    </div>
  );
}

export default Dashboard;