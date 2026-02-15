import React, { useContext, useState } from "react";
import Loginvalidation from "./loginValidation";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import UserContext from "../Context/userContext";

function Login() {
  const [values, setValues] = useState({
    email: "",
    password: "",
  });

  const [error, setError] = useState({});
  const handleInput = (event) => {
    setValues((prev) => ({ ...prev, [event.target.name]: event.target.value }));
  };
  const navigate = useNavigate();
  axios.defaults.withCredentials = true;

  const { setUserName } = useContext(UserContext);

  const handleSubmit = async (event) => {
    event.preventDefault();
    const errors = Loginvalidation(values);
    setError(errors);

    if (errors.email === "" && errors.password === "") {
      try {
        const res = await axios.post(
          "http://localhost:8081/auth/login",
          values
        );
        if (res.data.role.toLowerCase() === "student") {
          setUserName(res.data.userName);
          navigate("/studenthomedash");
        } else if (res.data.role.toLowerCase() === "teacher") {
          setUserName(res.data.userName);
          navigate("/teacherhomedash");
        }
      } catch (err) {
        console.log(err);
        console.log("Data Invalid" + error);
        alert("Login failed. Please check your credentials :");
      }
    } else {
      alert("E-mail or Password is incorrect");
    }
  };
  return (
    <div>
      {/* <NavBar/> */}
      <div
        className="d-flex justify-content-center align-items-center  vh-100"
        style={{ backgroundColor: "#7da0ca" }}
      >
        <div className="bg-white p-3 rounded w-25">
          <h2>Login</h2>
          <form onSubmit={handleSubmit}>
            <div className="mb-3 ">
              <label htmlFor="email">
                <strong>E-mail</strong>
              </label>
              <input
                type="email"
                placeholder="Enter Email"
                className="form-control rounded-0"
                name="email"
                id="email"
                onChange={handleInput}
              />
              {error.email && (
                <span className="text-danger">{error.email}</span>
              )}
            </div>
            <div className="mb-3 ">
              <label htmlFor="password">
                <strong>Password</strong>
              </label>
              <input
                type="password"
                placeholder="Enter Password"
                className="form-control rounded-0"
                name="password"
                id="password"
                onChange={handleInput}
              />
              {error.password && (
                <span className="text-danger">{error.password}</span>
              )}
            </div>

            <button type="submit" className="btn btn-success w-100">
              Login
            </button>
            <Link to="/studentsignup" className="btn  btn-secondary w-100 mt-3">
              Register Account
            </Link>
          </form>
        </div>
      </div>
    </div>
  );
}

export default Login;
