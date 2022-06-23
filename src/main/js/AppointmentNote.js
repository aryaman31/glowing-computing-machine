import React from 'react'
import { Link } from 'react-router-dom'

export default function AppointmentNote() {
  return (
    <>
    <h1>Your note from the meeting</h1>
    {/* TODO: get from database */}
    <hr/>
    <h3>Doctor:</h3>
    <p>Dr Phil</p>

    <h3>Note: </h3>
    <li>Drink more liquids.</li>
    <li>Eat less carbohydrates, such as chips and bread.</li>
    <li>Have less sodium(salt) in diet</li>


    <hr/>

    <div className='row'>
        <Link to="/view" className='btn'>Back</Link>
    </div>
    </>
  )
}
