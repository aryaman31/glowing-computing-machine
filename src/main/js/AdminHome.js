import React from 'react';
import { Link } from 'react-router-dom';

export default function AdminHome( {adminName}) {
  return (
    <>
      <nav className='navbar'>
        <h1>Welcome, {adminName}!</h1>
        <h2>What would you like to do?</h2>

        <div className='options'>

          <Link to="/patient_booking_requests">
            <button>Manage patient booking requests</button>
          </Link>

          <br/>

      <Link to="/admin_login">
        <button>Back</button>
      </Link>

      <br/>

<Link to="/">
  <button>Log out</button>
</Link>


        </div>

      </nav>
    </>
        
  )
}
