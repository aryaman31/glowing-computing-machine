import React from 'react';
import { Link } from 'react-router-dom';

export default function AdminHome( {adminName}) {
  return (
    <div className='fit-to-page center column'>
      <h1>Welcome, {adminName}!</h1>
      <h2>What would you like to do?</h2>

      <div className='options login-form'>

        <Link to="/patient_booking_requests" className='btn'>
          Manage patient booking requests
        </Link>

        <br/>

        <Link to="/admin_login" className='btn'>
          Log out
        </Link>

        <br/>


      </div>

    
    </div>
        
  )
}
