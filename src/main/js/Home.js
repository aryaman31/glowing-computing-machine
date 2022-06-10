import React from 'react';
import { Link } from 'react-router-dom';

export default function Home({ name }) {
  return (
    <>
      <nav className='navbar'>
        <h1>Welcome {name}!</h1>
        <h2>What would you like to do?</h2>

        <div className='options'>

          <Link to="/bookings">
            <button>Book an appointment</button>
          </Link>

          <Link to="/view">
            <button>View your appointment</button>
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
