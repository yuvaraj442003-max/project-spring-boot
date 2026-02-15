import React, { useContext } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { Link, useNavigate } from "react-router-dom";
import "./sidebar.css";
import axios from "axios";
import UserContext from "../Context/userContext";

const NavBarTeacher = () => {
  const { setUserName } = useContext(UserContext);

  const navigate = useNavigate();
  const handleLogout = () => {
    axios
      .post("http://localhost:8081/auth/logout")
      .then((response) => {
        if (response.data.logout === "Logout Successfull") {
          console.log("Logged out successfully");
          setUserName(null);
          navigate("/");
        }
      })
      .catch((error) => {
        console.log("Error logging out:", error);
      });
  };

  return (
    <div className="sidebar">
      <div className="m-2">
        <i className="bi bi-rocket-takeoff-fill me-2 fs-4"></i>
        <span className="brand-name fs-4">Teacher</span>
      </div>
      <hr className="text-dark" />
      <div className="list-group list-group-flush">
        <Link to="/" className="list-group-item py-2 rounded sidelink">
          <i className="bi bi-house fs-5 me-3"></i>
          <span>Home</span>
        </Link>
        <Link
          to="/createtestpage"
          className="list-group-item py-2 rounded sidelink"
        >
          <i className="bi bi-plus-lg fs-5 me-3"></i>
          <span>CreateTest</span>
        </Link>

        <Link
          to="/reportteacher"
          className="list-group-item py-2 rounded sidelink"
        >
          <i className="bi bi-clipboard-data fs-5 me-3"></i>
          <span>Report</span>
        </Link>

        <Link
          to="/profileteacher"
          className="list-group-item py-2 rounded sidelink"
        >
          <i className="bi bi-person fs-5 me-3"></i>
          <span>Profile</span>
        </Link>
        <button
          onClick={handleLogout}
          className="list-group-item py-2 rounded btn sidelink"
        >
          <i className="bi bi-power fs-5 me-3"></i>
          <span style={{ marginRight: "100px" }}>Logout</span>
        </button>
      </div>
    </div>
  );
};

export default NavBarTeacher;
