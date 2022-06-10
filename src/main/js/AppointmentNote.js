import React from 'react'
import { Link } from 'react-router-dom'

export default function AppointmentNote() {
  return (
    <>
    <h1>Your note from the meeting</h1>
    {/* TODO: get from database */}
    <p>TODO: get note data from database</p>

    <div>
        <Link to="/view">
          <button>Back</button>
        </Link>
    </div>
    </>
  )
}
