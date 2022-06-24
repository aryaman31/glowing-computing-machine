import React from 'react'
import { Link } from 'react-router-dom'

export default function PreviousAppointments({ prevappoint }) {
  return (
    <>
    <p>Time: {prevappoint.time} </p>
    <p>Doctor: {prevappoint.doctor}</p>
    <Link to="/note" className='btn small-width'>View Note</Link>
    <hr/>
    </>
  )
}
