import React from 'react'
import AppointmentList from './AppointmentList'
import { Link } from 'react-router-dom'
import PreviousAppointments from './PreviousAppointments'

export default function ViewAppointmentPage({ upcomings, setUpcoming, allAppoints, setAllAppoints }) {

  const placeholder = {id: 1, 
    doctor: "Dr Phil",
    time: 'Tuesday, 12 March, 10:00-10:15', 
    problem: "placeholder", 
    description: "placeholder"}

  return (
    <>
      <h1>Your appointments</h1>
      
      <div>
      <p style={{fontWeight: 'bold'}}>Upcoming appointments:</p>
        <AppointmentList 
          upcomings={upcomings} 
          setUpcoming={setUpcoming}
          allAppoints={allAppoints} 
          setAllAppoints={setAllAppoints} />
      </div>

      <br/>

      <div>
      <p style={{fontWeight: 'bold'}}>Previous appointments:</p>
        {/* TODO: get previous appointments from database and remove placeholder */}
        {/* <p>placeholder</p> */}
        <PreviousAppointments prevappoint={placeholder} />
      </div>

      <br/>
      <div>
        <Link to="/home">
          <button>Back</button>
        </Link>
      </div>
    </>  
  )
}
