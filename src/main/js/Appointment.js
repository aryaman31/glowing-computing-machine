import React from 'react'
import { Link } from 'react-router-dom'

export default function Appointment({ appointment }) {

  return (
    <>
    <p>
      {appointment.time} {appointment.doctor}
    </p>
    <Link to={`/your_appointment/${appointment.id}`}>
      <button>Details</button>
    </Link>
    </>
  )
}
