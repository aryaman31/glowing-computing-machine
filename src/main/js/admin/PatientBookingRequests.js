import React, { Fragment, useEffect, useState } from 'react'
import { Link } from 'react-router-dom'
import { Calendar, Views } from 'react-big-calendar'
import "./../css/react-big-calendar.css"
import PatientAppointment from './../patients/PatientAppointment';
import { localizer } from './../patients/BookPageThree';
import Select from 'react-select';
import { returnNoSameSlots } from './App';

export const doctorDummyOptions = [{value: 'Dr Smith' , label: 'Dr Smith'},
{value: 'Dr Garcia' , label: 'Dr Garcia'},
{value: 'Dr Jones' , label: 'Dr Jones'} ]

export default function PatientBookingRequests({ allAppoints }) {

  const [thisDaySlots, setThisDaySlots] = useState([])
  const [currDoctor, setCurrDoctor] = useState("")
  const [doctorSlots, setDoctorSlots] = useState([])


  useEffect(() => {
    console.log({currDoctor})
    const temp = [...allAppoints]
    const res = temp.filter(elem => elem.doctor === currDoctor)
    //returnNoSameSlot so no overlap in display
    setDoctorSlots(returnNoSameSlots(res))
    setThisDaySlots([])
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


        <Select
          options={doctorDummyOptions}
          onChange={(e) => {setCurrDoctor(e.value)}} 
          placeholder="Choose a doctor from the dropdown below"
        />

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
              <Fragment key={a.id}>
                <PatientAppointment appoint={a}/>
                <button>Send confirmation</button>
                <hr/>
              </Fragment>
            )
          })}
          
        </div>

        <br/>
        <div className='row'>
          <Link to="/admin_home" className='btn'>Back</Link>
        </div>
    </>
        
  )
}
