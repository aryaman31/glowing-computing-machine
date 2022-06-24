import React from 'react'
import { Link, useParams } from 'react-router-dom'
import AppointmentDetail from '../widgets/AppointmentDetail'

export default function YourAppointmentPage({ upcomings }) {

  const { aid } = useParams()
  const temp = [...upcomings]
  const appoint = temp.find(appoint => appoint.id === aid)


  return (
    <>
      <h1>Appointment details</h1>

      {/* <p>TODO: add queue interface for expected wait times</p> */}

      <p>---------------------------------------------------------------------</p>

      <h2>Appointment: </h2>

      <AppointmentDetail appoint={appoint}/>

      <div>
        <p>---------------------------------------------------------------------</p>
        <p>To enquire about your appointment dial: 020 7794 0500</p>
      </div>


      <div>
        <Link to="/view" className='btn'>Back</Link>
     </div>
      
    </>
  )
}
