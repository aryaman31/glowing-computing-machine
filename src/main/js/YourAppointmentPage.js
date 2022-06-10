import React from 'react'
import { Link, useParams } from 'react-router-dom'
import Appointment from './Appointment'

export default function YourAppointmentPage({ upcomings }) {

  const { aid } = useParams()
  const temp = [...upcomings]
  const appoint = temp.find(appoint => appoint.id === aid)


  return (
    <>
      <h1>Appointment details</h1>

      <p>TODO: add queue interface for expected wait times</p>

      <div className='Appointment'>
        <p>Appointment: </p>
        <p>{appoint.time}</p>
        <p>{appoint.doctor}</p>
        <p>{appoint.problem}</p>
        <p>{appoint.description}</p>
        
      </div>


      <div>
        <Link to="/view">
          <button>Back</button>
        </Link>
     </div>
      
    </>
  )
}
