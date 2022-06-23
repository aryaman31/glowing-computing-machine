import React, { useCallback, useRef, useState } from 'react'
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



export default function BookAppointmentPage({ setUpcoming, name, allAppoints, setAllAppoints, doctor, setDoctor }) {

  const doctorRef = useRef()
  const problemRef = useRef()
  const symptomsRef = useRef()
  const durationRef = useRef()
  const extraInfoRef = useRef()
  const descriptionRef = useRef()


  const [startTime, setStartTime] = useState(new Date())
  const [endTime, setEndTime] = useState(new Date())  
  const [hasSelected, setHasSelected] = useState(false)

  const [category, setCategory] = useState("")

  const [appointmentType, setAppointmentType] = useState()

  // // lets the user do up to 5 slots 
  // const 


  const slotPropGetter = useCallback((date) => {
    
    const d = new Date(date)
    const s = new Date(startTime)
    const e = new Date(endTime)

    if (hasSelected && d >= s && d < e) {
      return ({
        className: 'slotDefault',
        
          style: {
            backgroundColor: 'grey',
          },    
      }) 
    } 
  }, [startTime, endTime, hasSelected])
  

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
    setHasSelected(true)
    
    console.log("RESERVED SLOT CLICKED, SEND TO WAITING LIST")
  }

  function handleSelectedDoctor(doctor) {
    setDoctor(doctor)
  }
  
  function handleConfirmation(_e) {
    // const doctor = doctorRef.current.value
    const problem = problemRef.current.value
    const description = '--- NATURE OF APPOINTMENT ---' +
    '\nType of appointment: ' + category +
    '\nPreferred way of seeing doctor: ' + appointmentType +
    '\n--- DETAILS REGARDING ISSUE ---' +  
    '\nSymptoms: ' + symptomsRef.current.value + 
    '\nDuration of symptoms: ' + durationRef.current.value  + 
    '\nAny extra information: ' + extraInfoRef.current.value 
    

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
    <h1>Let's book an appointment!</h1>

    <hr/>


    
    <h2> Step 1: Please consider whether your condition is appropriate to see a GP </h2>

    <div style={{
    alignItems: 'center',
    justifyContent: 'center',
}}>


    <p> Firstly, are you experiencing any of the following symptoms? </p>

    <ul>
              <li>Chest pain</li>
              <li>Broken bones</li>
              <li>Bleeding that won't stop</li>
              <li>Difficulty completing a sentence</li>
        </ul>

    <h3> If so, your condition is too urgent to see a GP, please go to A and E or dial 999. </h3>

    </div>

    <p style = {{alignItems: 'center'}}>---------------------------------------------------------------------</p>


    <div style={{
    // display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
}}>
    <p> Alternatively, is your problem limited to any of the following symptoms (experienced for a short amount of time)? </p>

    <ul>
              <li>Rash</li>
              <li>Runny nose</li>
              <li>Sore throat</li>
        </ul>

    <p> If so, it's likely your condition is a cold or a mild allergy. Please visit your local pharmacy for appropriate remedies, and come back if the symptoms remain for a long time </p>

    </div>

    <hr/>



{/*-------------------------------------------------------------------------------------------------*/}


    <h2>Step 2: Let us know how you'd like to be treated </h2>
 
    <div>
      <p>Please select the type of appointment you would like:</p>
      
      <select id="categories" value={category}  
      defaultValue={"default"}
              onChange={(e) => setCategory(e.target.value)}>
        <option value={"default"} disabled>
          Choose a category from the dropdown below
        </option>
        <option value="a routine checkup">Routine checkup</option>
        <option value="a blood test">Blood test</option>
        <option value="discuss blood test result">Discuss blood test result</option>
        <option value="a referral request">Referral request</option>
        <option value="an appointment">Other (e.g. unusual pain)</option>

      </select>

        </div>
        <p>Let us know if you'd like an in-person appointment, telephone appointment or home appointment. </p>
        <p><i>Please note, due to high demand for in-person appointments, certain appointments will be required to be taken over the phone.</i> </p>

        <select id="appointment type" value={appointmentType}  
      defaultValue={"default"}
              onChange={(e) => setAppointmentType(e.target.value)}>
        <option value={"default"} disabled>
          Choose your preferred way of seeing a doctor from the dropdown below
        </option>
        <option value="face-to-face">In-person</option>
        // can't do blood tests over the phone!
        <option value="phone appointment" disabled={category === "a blood test"}>Phone</option>
        <option value="home visit">Home visit</option>

      </select>

 

         {/*-------------------------------------------------------------------------------------------------*/}


      <h2>{(category === "discuss blood test result") ? "Remind us why you required a blood test" : ("Tell us why you would like " + category)} </h2>


        <div>
      <p> A brief description of the problem </p>
      <input ref={problemRef} type="text" size={50} placeholder='eg. "Persistent back-ache"'/>
    </div>



    <div>
      <p>Any symptoms and their severity </p>
      <input ref={symptomsRef} type="text" size={50} placeholder='eg. "Severe back-pain, mild swelling of knees"'/>
    </div>

    <div>
      <p> Duration of symptoms </p>
      <input ref={durationRef} type="text" size={50} placeholder='eg. "3 weeks"'/>
    </div>

    <div>
      <p>(Optional) Anything else the doctor might like to know?</p>
      <textarea
        ref={extraInfoRef}
        rows={5}
        cols={50}
        placeholder='eg. "Experiencing strong pain in upper-back for 4 weeks"'></textarea>
    </div>

    {/* <div>
      <p> (Optional) Anything else the doctor might like to know? </p>
      <input type="text" size={50} placeholder='eg. "3 weeks"'/>
    </div> */}

    


    {/* <div>
      <p>2. Give further details you wish to share:</p>
      <textarea
        ref={descriptionRef}
        rows={20}
        cols={50}
        placeholder='eg. "Experiencing strong pain in upper-back for 4 weeks"'></textarea>
    </div> */}

    <br/>

    <hr/>

{/*-------------------------------------------------------------------------------------------------*/}

    <h2>Pick an appointment slot</h2>


<div>
      <p>Please select a doctor you would like to meet</p>
      
      <select id="doctors" value={doctor}  
      defaultValue={"default"}
              onChange={(e) => setDoctor(e.target.value)}>
        <option value={"default"} disabled>
          Choose a doctor from the dropdown below
        </option>
        <option value="Dr Smith">Dr Smith</option>
        <option value="Dr Garcia">Dr Garcia</option>
        <option value="Dr Jones">Dr Jones</option>
      </select>

  <p><b>Selected doctor: </b> {doctor}</p>
        </div>


    

      {/* // TODO: Enable multiple slots  */}
    <p>Please select a date you would like to have your appointment.</p> 
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
        style={{ height: 400, width: 550, margin: "25px" }}
        slotPropGetter={slotPropGetter}
      />
    </div>

    <Slot startTime={startTime} endTime={endTime} hasSelected={hasSelected} />
      
    <br/>  
    
    <div className='row'>
      <Link to="/view" className='btn' onClick={handleConfirmation}> Confirm</Link>

      <Link to="/home" className='btn'>Back</Link>
    </div>  

    </>
  )
}

