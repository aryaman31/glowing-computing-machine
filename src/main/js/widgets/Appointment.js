import React from 'react'
import { Link } from 'react-router-dom'

export default function Appointment({ appointment }) {

  return (
    <div>
      <p>Time: {appointment.time}</p>
      <p>Doctor: {appointment.doctor}</p>
      <Link to={`/your_appointment/${appointment.id}`} className='btn small-width'> Details</Link>
    </div>
  )
}
