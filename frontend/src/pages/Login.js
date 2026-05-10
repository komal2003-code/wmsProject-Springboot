import { useState } from "react";
import API from "../services/api";
import { useNavigate } from "react-router-dom";
import "./Login.css";

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

      console.log("LOGIN RESPONSE :", res);

      localStorage.setItem("token", res.data);

      alert("Login Success");

      navigate("/dashboard");

    } catch (error) {

      console.log("FULL ERROR :", error);

      console.log("ERROR RESPONSE :", error.response);

      console.log("ERROR DATA :", error.response?.data);

      console.log("STATUS :", error.response?.status);

      alert("Login Failed");
    }
  };

  return (

    <div className="login-container">

      <h2>Login</h2>

      <input
        type="text"
        placeholder="Username"
        value={username}
        onChange={(e) => setUsername(e.target.value)}
      />

      <input
        type="password"
        placeholder="Password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
      />

      <button onClick={handleLogin}>
        Login
      </button>

    </div>
  );
}

export default Login;