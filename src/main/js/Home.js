import React from 'react';
import { Link } from 'react-router-dom';

export default function Home({ name }) {
  return (
    <div className='fit-to-page center column'>
      <h1>Welcome, {name}!</h1>
      <h2>What would you like to do?</h2>

      <div className='options login-form'>

        <Link to="/bookings" className='btn'>Book an appointment</Link>

        <br/>


        <Link to="/view" className='btn'>View appointments</Link>

        <br/>

        <Link to="/" className='btn'>Log out</Link>

      </div>
    </div>
        
  )
}
