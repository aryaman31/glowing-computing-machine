import React, { useRef, useState } from 'react'
import { Link } from 'react-router-dom'




export default function BookPageOne({ name, setProblem, setDescription }) {

  const problemRef = useRef()
  const symptomsRef = useRef()
  const durationRef = useRef()
  const extraInfoRef = useRef()

  const [category, setCategory] = useState("")

  const [appointmentType, setAppointmentType] = useState()



  function handleConfirmation(_e) {
    setProblem(problemRef.current.value)
    setDescription('--- NATURE OF APPOINTMENT ---' +
    '\nType of appointment: ' + category +
    '\nPreferred way of seeing doctor: ' + appointmentType +
    '\n--- DETAILS REGARDING ISSUE ---' +  
    '\nSymptoms: ' + symptomsRef.current.value + 
    '\nDuration of symptoms: ' + durationRef.current.value  + 
    '\nAny extra information: ' + extraInfoRef.current.value)
    
  }


    return (
        <>

            {/* heading */}

            <h1>Let us know more about your appointment, {name}</h1>
            
            <hr/>


            {/* type of appointment*/}
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


                {/* way of seeing doctor */}
            <div>
                <p>Would you like an in-person appointment, telephone appointment or home appointment? </p>
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
            </div>



            {/* enter details about problem*/}

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
            <input ref={durationRef} type="text" size={50} required placeholder='eg. "3 weeks"'/>
            </div>

            <div>
            <p>(Optional) Anything else the doctor might like to know?</p>
            <textarea
                ref={extraInfoRef}
                rows={5}
                cols={50}
                placeholder='eg. "Experiencing strong pain in upper-back for 4 weeks"'></textarea>
            </div>

            <div className='row'>
                <Link to="/home" onClick={handleConfirmation} className='btn'>Cancel</Link>

                <Link to="/appointment_slot" onClick={handleConfirmation} className='btn'>Next</Link>
            </div>

            <h3> Step 2 of 3</h3>

        </>
    )

}

