import React from 'react';
import { Link } from 'react-router-dom';

export default function PatientBookingRequests() {
  return (
    <>
        <h1>View patient's booking requests below</h1>
        
        <div className='options'>

        <Link to="/admin_home">
        <button>Back</button>
        </Link>

        </div>
    </>
        
  )
}
