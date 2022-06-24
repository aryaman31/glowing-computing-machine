import React from 'react';
import { Link } from 'react-router-dom';
import "./css/stylesheet.css";

export default function AdminOrPatient() {

  return (
    <div className='center welcome-page column fit-to-page'>
      <h1>Welcome!</h1>
      <h2>Are you a patient or an administrator?</h2>

      <div className='row'>
        <Link to="/patient_login" className='btn'>
          Patient
        </Link>

        <Link to="/admin_login" className='btn'>
          Admin
        </Link>
      </div>
    </div>
  )
}
