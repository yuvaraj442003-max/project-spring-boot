import React, { useContext } from "react";
import "bootstrap/dist/css/bootstrap.min.css";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import "./studentSidebar.css";
import UserContext from "../Context/userContext";

const NavBarStudent = () => {
  const navigate = useNavigate();

  const { setUserName } = useContext(UserContext);

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
    <div className=" sidebar">
      <div className="m-2 ">
        <i className="bi bi-sourceforge me-2 fs-4"></i>
        <span className="brand-name fs-4">Student</span>
      </div>
      <hr className="text-dark" />
      <div className="  list-group list-group-flush">
        <Link to="/" className="list-group-item py-2 rounded sidelink">
          <i className="bi bi-house fs-5 me-3"></i>
          <span>Home</span>
        </Link>

        <Link
          to="/reportstudent"
          className="list-group-item py-2 rounded sidelink"
        >
          <i className="bi bi-clipboard-data fs-5 me-3"></i>
          <span>Report</span>
        </Link>

        <Link
          to="/profilestudent"
          className="list-group-item py-2 rounded sidelink"
        >
          <i className="bi bi-person fs-5 me-3"></i>
          <span>Profile</span>
        </Link>
        <button
          onClick={handleLogout}
          className="list-group-item py-2 rounded btn  sidelink"
        >
          <i className="bi bi-power fs-5 me-3"></i>
          <span style={{ marginRight: "100px" }}>Logout</span>
        </button>
      </div>
    </div>
  );
};

export default NavBarStudent;
