import React, { useContext } from "react";
import "bootstrap/js/dist/dropdown";
import "bootstrap/js/dist/collapse";
import UserContext from "../Context/userContext";

const TopNavStudent = ({ Toggle }) => {
  const { userName } = useContext(UserContext);

  return (
    <nav
      className="navbar navbar-expand-sm navbar-white px-3  "
      style={{ backgroundColor: "#052659", color: "#c1e8ff" }}
    >
      <i
        className="navbar-brand bi bi-justify-left fs-4 text-white "
        style={{ cursor: "pointer" }}
        onClick={Toggle}
      ></i>
      <h2>Student DashBoard</h2>
      <div
        className="ml-auto"
        style={{
          position: "absolute",
          right: "20px",
          display: "flex",
          alignItems: "center",
        }}
      >
        <i
          className="  bi bi-person-circle"
          style={{ paddingRight: "10px", fontSize: "2rem" }}
        ></i>
        <h4
          className="mb-0 text-white"
          style={{ borderBottom: "2px solid #c1e8ff", paddingBottom: "3px" }}
        >
          {userName}
        </h4>
      </div>
    </nav>
  );
};

export default TopNavStudent;
