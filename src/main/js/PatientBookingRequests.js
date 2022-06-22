import React, { useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { Calendar, Views, dateFnsLocalizer } from 'react-big-calendar'
import format from "date-fns/format";
import parse from "date-fns/parse";
import startOfWeek from "date-fns/startOfWeek";
import getDay from "date-fns/getDay"
import "./react-big-calendar.css"
import PatientAppointment from './PatientAppointment';

const locales = {
  "en-GB": require("date-fns/locale/en-GB")
};

const localizer = dateFnsLocalizer({
  format,
  parse,
  startOfWeek,
  getDay,
  locales
});

export default function PatientBookingRequests({ allAppoints }) {

  const [thisDaySlots, setThisDaySlots] = useState([])
  const [currDoctor, setCurrDoctor] = useState("")
  const [doctorSlots, setDoctorSlots] = useState([])
  const [confirmedSlot, setConfirmedSlot] = useState()


  function handleDoctorSelection(e) {
    console.log({currDoctor})
    setCurrDoctor(e.target.value)
  }


  useEffect(() => {
    console.log({currDoctor})
    const temp = [...allAppoints]
    const res = temp.filter(elem => elem.doctor === currDoctor)
    setDoctorSlots(res)
  }, [currDoctor])


  function handleSelectEvent(e) {
    
    const temp = [...allAppoints]
    const thisSlotApppoints = temp.filter(elem => elem.start === e.start && elem.doctor === currDoctor)
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
          />
        </div>

        {confirmedSlot && <div>
          <h3>Confirmed appointment for slot:</h3>
          <PatientAppointment appoint={confirmedSlot}/>
        </div>}

        <hr/>


        <h3>Reserved appointment slots:</h3>

        <div className='currSlotAppointments'>
          {thisDaySlots.map(a => {
            return (
              <>
              <div key={a.id}>
                <PatientAppointment appoint={a}/>
                <button onClick={(e) => {setConfirmedSlot(a)}}>Send confirmation</button>
              </div>
              <hr/>
              </>
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
