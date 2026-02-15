import React, { useState, useEffect } from "react";
import axios from "axios";
import "bootstrap/dist/css/bootstrap.min.css";
import { useNavigate } from "react-router-dom";

const ReportTeacher = () => {
  const [reportData, setReportData] = useState([]);
  useEffect(() => {
    const fetchMarks = async () => {
      try {
        const res = await axios.get("http://localhost:8081/mark/teacherReport");
        setReportData(res.data);
        console.log(res.data);
      } catch (error) {
        console.log("Error fetching marks:", error);
      }
    };

    fetchMarks();
  }, []);
  const getPercentageColor = (percentage) => {
    if (percentage >= 80) {
      return "green";
    } else if (percentage >= 60) {
      return "orange";
    } else {
      return "red";
    }
  };

  const getSkillMessage = (percentage) => {
    if (percentage >= 80) {
      return "Excellent";
    } else if (percentage >= 60) {
      return "Good, keep learning";
    } else {
      return "Need to improve your skill";
    }
  };

  const navigate = useNavigate();
  const handleBackButtonClick = () => {
    navigate("/teacherhomedash");
  };

  return (
    <div className="container mt-5">
      <h1 className="text-center mb-4">Test Report for All Students</h1>
      <div className="card rounded shadow-lg">
        <div className="card-body" style={{ backgroundColor: "#7da0ca" }}>
          <div className="table-responsive">
            <table className="table table-striped table-hover rounded">
              <thead className="table-dark">
                <tr>
                  <th scope="col">S.No</th>
                  <th scope="col">Student Name</th>
                  <th scope="col">Test Name</th>
                  <th scope="col">Marks</th>
                  <th scope="col">Student Level</th>
                  <th scope="col">Cheating Count</th>
                  <th scope="col">Date</th>
                </tr>
              </thead>
              <tbody>
                {reportData.length > 0 ? (
                  reportData.map((data, index) => {
                    const percentage = (
                      (data.mark / data.totalQuestions) *
                      100
                    ).toFixed(2);
                    return (
                      <tr key={index}>
                        <td>{index + 1}</td>
                        <td>{data.studentName}</td>
                        <td>{data.testName}</td>
                        <td>{data.mark}</td>
                        <td
                          className="fw-bold"
                          style={{ color: getPercentageColor(percentage) }}
                        >
                          {percentage}% - {getSkillMessage(percentage)}
                        </td>
                        <td>{Math.floor(data.cheatingCount / 10)}</td>
                        <td>{new Date(data.Date).toLocaleDateString()}</td>
                      </tr>
                    );
                  })
                ) : (
                  <tr>
                    <td colSpan="7" className="text-center">
                      No report data available.
                    </td>
                  </tr>
                )}
              </tbody>
            </table>
          </div>
          <div className="d-flex justify-content-center mt-3">
            <button
              className="btn btn-secondary"
              onClick={handleBackButtonClick}
            >
              Go Back
            </button>
          </div>
        </div>
      </div>
    </div>
  );
};

export default ReportTeacher;
