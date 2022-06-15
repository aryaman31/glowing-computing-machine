import React, { useRef, useState } from 'react'
import { Link } from 'react-router-dom'
import { v4 as uuidv4 } from 'uuid';
import { Calendar, Views, dateFnsLocalizer } from 'react-big-calendar'
import format from "date-fns/format";
import parse from "date-fns/parse";
import startOfWeek from "date-fns/startOfWeek";
import getDay from "date-fns/getDay"
import "./react-big-calendar.css"
import Slot from './Slot';

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


export default function BookAppointmentPage({ setUpcoming, name, allAppoints, setAllAppoints }) {

  const doctorRef = useRef()
  const problemRef = useRef()
  const descriptionRef = useRef()


  const [startTime, setStartTime] = useState(new Date())
  const [endTime, setEndTime] = useState(new Date())  
  const [hasSelected, setHasSelected] = useState(false)

  

  function handleSelectSlot({start, end}) {
    setStartTime(start)
    setEndTime(end)
    setHasSelected(true)

    console.log(start)
    console.log(end)
  }

  function handleSelectEvent(e) {
    setStartTime(e.start)
    setEndTime(e.end)
    console.log("RESERVED SLOT CLICKED, SEND TO WAITING LIST")
  }

  function handleConfirmation(_e) {
    const doctor = doctorRef.current.value
    const problem = problemRef.current.value
    const description = descriptionRef.current.value
    if (doctor === '' || problem === '' || description === '' || !startTime || !endTime ) return

        
    const options = {  weekday: 'long', month: 'long', day: 'numeric' }
    const printDate = new Date(startTime).toLocaleString('en-GB', options)

    const options2 = { hour: 'numeric', minute: 'numeric' }
    const printStartTime = new Date(startTime).toLocaleString('en-GB', options2)
    const printEndTime = new Date(endTime).toLocaleString('en-GB', options2)

    const newId = uuidv4()

    //add to upcomings list
    setUpcoming(prev => {
      return [...prev, { id: newId, 
        doctor: doctor,
        time: printDate + ' ' + printStartTime + '-' + printEndTime, 
        problem: problem, 
        description: description}]
    })

    //add to all appointments
    setAllAppoints(prev => {
      return [...prev, { start: startTime, end: endTime, title: "lul", id: newId}]
    })

    //TODO: actually use the data and send to database
    console.log(name)
    console.log(doctor)
    console.log(problem)
    console.log(description)
    console.log(printDate + ' ' + printStartTime + '-' + printEndTime)
 
  }


  return (
    <>
    <h1>Let's book an appointment</h1>
    <div>
      <p>Please select a doctor you would like to meet</p>
      <input ref={doctorRef} type="text" size={32} placeholder='Type "any" if you have no preference'/>
    </div>

{/*-------------------------------------------------------------------------------------------------*/}
  
    <p>Please select a date you would like to have your appointment</p>
    <p>Blue boxes indicates that the slot has already been reserved</p>
    <p>You can choose to be notified when a reserved slot becomes available by clicking it.</p>
    
    <div className="App">
      <Calendar
        localizer={localizer}
        defaultView={Views.WEEK}
        step = {15}
        timeslots = {1}
        views = {['week']}
        events = {allAppoints}
        onSelectEvent={handleSelectEvent}
        onSelectSlot={handleSelectSlot}
        selectable
        onSelecting={() => false}
        style={{ height: 400, width: 550, margin: "50px" }}
      />
    </div>

    <Slot startTime={startTime} endTime={endTime} hasSelected={hasSelected} />


   

{/*-------------------------------------------------------------------------------------------------*/}

    <h1>Tell us about your condition</h1>
    <div>
      <p>1. What is your problem:</p>
      <input ref={problemRef} type="text" size={50} placeholder='eg. "Persistent back-ache"'/>
    </div>

{/*-------------------------------------------------------------------------------------------------*/}

    <div>
      <p>2. Give further details you wish to share:</p>
      <textarea
        ref={descriptionRef}
        rows={20}
        cols={50}
        placeholder='eg. "Experiencing strong pain in upper-back for 4 weeks"'></textarea>
    </div>

    <br/>

    <Link to="/view">
      <button onClick={handleConfirmation}>Confirm</button>
    </Link>

    <Link to="/home">
      <button>Back</button>
    </Link>

    </>
  )
}

