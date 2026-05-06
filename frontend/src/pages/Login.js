import { useState } from "react";
import API from "../services/api";
import { useNavigate } from "react-router-dom";

function Login() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleLogin = async () => {
    try {
      const res = await API.post("/auth/login", {
        username,
        password
      });

      localStorage.setItem("token", res.data.token);
      alert("Login Success");

      navigate("/dashboard");

    } catch {
      alert("Login Failed");
    }
  };

  return (
    <div>
      <h2>Login</h2>

      <input placeholder="Username"
        onChange={(e) => setUsername(e.target.value)}
      />

      <input type="password" placeholder="Password"
        onChange={(e) => setPassword(e.target.value)}
      />

      <button onClick={handleLogin}>Login</button>
    </div>
  );
}

export default Login;