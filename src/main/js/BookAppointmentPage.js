import React, { useRef } from 'react'
import { Link } from 'react-router-dom'
import { v4 as uuidv4 } from 'uuid';

export default function BookAppointmentPage({ setUpcoming, name }) {

  const doctorRef = useRef()
  const problemRef = useRef()
  const descriptionRef = useRef()

  function handleConfirmation(e) {
    const doctor = doctorRef.current.value
    const problem = problemRef.current.value
    const description = descriptionRef.current.value
    if (doctor === '' || problem === '' || description === '') return

    //add to upcomings list
    setUpcoming(prev => {
      return [...prev, { id: uuidv4(), 
        doctor: doctor,
        time: 'Tuesday, 7 March, 09:00-09:15', 
        problem: problem, 
        description: description}]
    })


    //TODO: actually use the data and send to database
    console.log(name)
    console.log(doctor)
    console.log(problem)
    console.log(description)
 
  }


  return (
    <>
    <h1>Let's book an appointment</h1>
    <div>
      <p>Please select a doctor you would like to meet</p>
      <input ref={doctorRef} type="text" size={32} placeholder='Type "any" if you have no preference'/>
    </div>

    
    <div>
      <p>-----------------------------------------------------------------</p>
      <p>Please select an appointment slot </p>

      {/* TODO: implement calendar interface and remove placeholder appointments */}
      <p>TODO: implement calendar interface</p>
      <p>Tuesday, 7 March, 09:00-09:15</p>
      <button>Dummy appointment book page</button>

      <p>You can choose to be notified when a reserved slot becomes available by clicking it.</p>
      
      <p>-----------------------------------------------------------------</p>
    </div>


    <h1>Tell us about your condition</h1>
    <div>
      <p>1. What is your problem:</p>
      <input ref={problemRef} type="text" size={50} placeholder='eg. "Persistent back-ache"'/>
    </div>

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
