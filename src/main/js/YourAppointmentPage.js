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
      <p>---------------------------------------------------------------------</p>
        <h2>Appointment: </h2>
        <div>
          <h3>Appointment time:</h3>
          <p>{appoint.time}</p>
        </div>

        <div>
          <h3>Doctor:</h3>
          <p>{appoint.doctor}</p>
        </div>

        <div>
          <h3>Problem faced:</h3>
          <p>{appoint.problem}</p>
        </div>
        
        <div>
          <h3>Description:</h3>
          <p>{appoint.description}</p>
        </div>
        
      </div>

      <div>
        <p>---------------------------------------------------------------------</p>
        <p>To enquire about your appointment dial: 020 7794 0500</p>
      </div>


      <div>
        <Link to="/view">
          <button>Back</button>
        </Link>
     </div>
      
    </>
  )
}
