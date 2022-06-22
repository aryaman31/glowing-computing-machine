import React, { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { Calendar, Views } from 'react-big-calendar'
import "./react-big-calendar.css"
import PatientAppointment from './PatientAppointment';
import { localizer } from './BookPageThree';

export default function PatientBookingRequests({ allAppoints, displayAppoints }) {

  const [thisDaySlots, setThisDaySlots] = useState([])
  const [currDoctor, setCurrDoctor] = useState("")
  const [doctorSlots, setDoctorSlots] = useState([])


  function handleDoctorSelection(e) {
    console.log(displayAppoints)
    setCurrDoctor(e.target.value)
  }


  useEffect(() => {
    console.log({currDoctor})
    const temp = [...displayAppoints]
    const res = temp.filter(elem => elem.doctor === currDoctor)
    setDoctorSlots(res)
  }, [currDoctor])


  function handleSelectEvent(e) {
    
    const temp = [...allAppoints]
    console.log(e.start)
    const thisSlotApppoints = temp.filter(elem => elem.start.getTime() === e.start.getTime() && elem.doctor === currDoctor)
    setThisDaySlots(thisSlotApppoints)
  }



  return (
    <>
        <h1>View patient's booking requests below</h1>

        <p>Please select a doctor to view their appointments</p>
      
        <select id="doctors" value={currDoctor}  
      defaultValue={"default"}
              onChange={handleDoctorSelection}>
        <option value={"default"} disabled>
          Choose a doctor from the dropdown below
        </option>
        <option value="Dr Smith">Dr Smith</option>
        <option value="Dr Garcia">Dr Garcia</option>
        <option value="Dr Jones">Dr Jones</option>
      </select>

        <p><b>Currently viewing the appointments of: </b> {currDoctor}</p>

        <hr/>


        <div className="App">
          <Calendar
            localizer={localizer}
            defaultView={Views.WEEK}
            step = {15}
            timeslots = {1}
            views = {['week']}
            events = {doctorSlots}
            onSelectEvent={handleSelectEvent}
            selectable
            onSelecting={() => false}
            style={{ height: 400, width: 550, margin: "25px" }}
            min={new Date(2022, 0, 0, 8, 0, 0)}
            max={new Date(2022, 0, 0, 20, 0, 0)}
          />
        </div>

        <hr/>


        <h3>Reserved appointment slots:</h3>

        <div className='currSlotAppointments'>
          {thisDaySlots.map(a => {
            return (
              <div key={a.id}>
                <PatientAppointment appoint={a}/>
                <button>Send confirmation</button>
                <hr/>
              </div>
            )
          })}
          
        </div>

        <br/>
        
        <Link to="/admin_home">
          <button>Back</button>
        </Link>
    </>
        
  )
}
