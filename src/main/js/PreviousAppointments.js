import React from 'react'
import { Link } from 'react-router-dom'

export default function PreviousAppointments({ prevappoint }) {
  return (
    <>
    <p>
      {prevappoint.time} {prevappoint.doctor}
    </p>
    <Link to="/note">
      <button>View note</button>
    </Link>
    </>
  )
}
