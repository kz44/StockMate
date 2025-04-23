import React from "react";
import "./Dashboard.css";

const Dashboard = () => {
  return (
    <div className="dashboard-hero">
      {/* Overlay layer for darkening the background and showing text */}
      <div className="overlay">
        <h1>Üdvözöllek a Tőzsde Kezelő Rendszerben</h1>
        <p>Kezeled a részvényeidet egy modern, letisztult felületen</p>
      </div>
    </div>
  );
};

export default Dashboard;
