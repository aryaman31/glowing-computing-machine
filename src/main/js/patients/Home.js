import React from 'react';
import { Link } from 'react-router-dom';

export default function Home({ nhsNumber }) {
  return (
    <div className='fit-to-page center column'>
      <h1>Welcome, {nhsNumber}!</h1>
      <h2>What would you like to do?</h2>

      <div className='options login-form'>

        <Link to="/book_appointment" className='btn'>Book an appointment</Link>

        <br/>


        <Link to="/view" className='btn'>View appointments</Link>

        <br/>

        <Link to="/patient_login" className='btn'>Log out</Link>

      </div>
    </div>
        
  )
}
