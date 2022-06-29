import React from 'react'
import { Link } from 'react-router-dom'

export default function Appointment({ appointment }) {

  return (
    <div>
      {appointment.confirm && <h2 style={{color: 'green'}}>Confirmed</h2>} 
      <p>Time: {appointment.time}</p>
      <p>Doctor: {appointment.doctor}</p>
      <Link to={`/your_appointment/${appointment.id}`} className='btn small-width'> Details</Link>
    </div>
  )
}
