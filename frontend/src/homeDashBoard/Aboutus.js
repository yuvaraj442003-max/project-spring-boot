import React from "react";
import { Link } from "react-router-dom";

const AboutUs = () => {
  return (
    <div className="container mt-5">
      <div className="row">
        <div className="col-lg-8 mx-auto text-center">
          <h1 className="mb-4">About Our Online Examination System</h1>
          <p className="lead">
            Our Online Examination System is a powerful platform designed to
            streamline the exam process for <b>Students</b> and <b>Teachers</b>.
            It provides an efficient, secure, and user-friendly experience for
            conducting tests online.
          </p>
        </div>
      </div>

      <div className="row mt-5">
        <div className="col-md-6">
          <h3>Key Features</h3>
          <ul className="list-group">
            <li className="list-group-item">
              📝 Easy test creation and management
            </li>
            <li className="list-group-item">
              📊 Real-time student performance tracking
            </li>
            <li className="list-group-item">
              🔒 Secure login with authentication
            </li>
            <li className="list-group-item">
              📂 Question bank with multiple formats
            </li>
            <li className="list-group-item">
              ⚡ Instant grading and result analysis
            </li>
          </ul>
        </div>

        <div className="col-md-6">
          <h3>Why Choose Us?</h3>
          <p>
            Our system leverages cutting-edge{" "}
            <i>
              <b>AI technology</b>
            </i>{" "}
            to ensure the integrity of online exams. With real-time biometric
            authentication, we verify student identity using face recognition
            before and during the test, preventing impersonation. Our AI-driven
            anti-cheating mechanisms continuously monitor behavior, detecting
            suspicious activities such as unauthorized face movement, multiple
            persons, or unusual screen activity. This ensures a fair, secure,
            and credible examination environment for students and educators.
          </p>
          <Link to="/" className="btn btn-primary mt-3">
            Home Page
          </Link>
        </div>
      </div>
    </div>
  );
};

export default AboutUs;
