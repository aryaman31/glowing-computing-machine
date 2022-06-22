import React from 'react';
import { Link } from 'react-router-dom';

export default function Home({ name }) {
  return (
    <>
      <nav className='navbar'>
        <h1>Welcome, {name}!</h1>
        <h2>What would you like to do?</h2>

        <div className='options'>

          <Link to="/book_appointment">
            <button>Book an appointment</button>
          </Link>

          <br/>


          <Link to="/view">
            <button>View my appointments</button>
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
