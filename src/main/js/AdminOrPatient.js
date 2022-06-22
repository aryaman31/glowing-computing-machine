import React from 'react';
import { Link } from 'react-router-dom';

export default function AdminOrPatient() {
  return (
    <>
      <nav className='navbar'>
        <h1>Welcome!</h1>
        <h2>Are you a patient or an administrator?</h2>

          <Link to="/patient_login">
            <button>Patient</button>
          </Link>

          <Link to="/admin_login">
            <button>Admin</button>
          </Link>



      </nav>
    </>
        
  )
}
