import React, { useEffect, useRef, useState } from 'react'
import { Link } from 'react-router-dom'
import Select from 'react-select';




export default function BookPageOne({ name, setProblem, setDescription }) {


  const problemRef = useRef()
  const symptomsRef = useRef()
  const durationRef = useRef()
  const extraInfoRef = useRef()

  const [category, setCategory] = useState("")

  const [appointmentType, setAppointmentType] = useState()

  const [appTypeOptions, updateTypeOptions] = useState([
    {value: 'face-to-face' , label: 'In-person'},
    {value: 'phone appointment', label: 'Phone', disabled: false},
    {value: 'home visit', label: 'Home visit'}
    ])


  const appointmentCategories = [
    {value: 'an appointment', label: 'New problem'},
    {value: 'a blood test', label: 'Blood test' },
    {value: 'discuss blood test result', label: 'Discuss blood test result' },
    {value: 'a referral request', label: 'Referral request' },
    {value: 'a routine checkup', label: 'Routine checkup' },]



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

  function handleCategory(e) {
    setCategory(e.value)
  }

  useEffect(() => {
    const temp = [...appTypeOptions]
    const index = temp.findIndex((obj) => obj.value === 'phone appointment')
    if (category === 'a blood test') {
      temp[index].disabled = true
    } else {
      temp[index].disabled = false  
    }
    updateTypeOptions(temp)
    console.log({appTypeOptions})

  }, [category])

  

    return (
        <>

            {/* heading */}

            <h1>Let us know more about your appointment, {name}</h1>
            
            <hr/>


            {/* type of appointment*/}
            <div>
            <p>Please select the type of appointment you would like:</p>

            <Select
              options={appointmentCategories}
              onChange={(e) => {handleCategory(e)}} 
              placeholder="Choose a category from the dropdown below"
            />
            
            </div>


            {/* way of seeing doctor */}

            <p>Would you like an in-person appointment, telephone appointment or home appointment? </p>
            <p><i>Please note, due to high demand for in-person appointments, certain appointments will be required to be taken over the phone.</i> </p>

            <Select
              options={appTypeOptions}
              onChange={(e) => {setAppointmentType(e.value)}}
              isOptionDisabled={(option) => option.disabled} 
              placeholder="Choose your preferred way of seeing a doctor from the dropdown below"
            />



            {/* enter details about problem*/}

            <h2>{(category === "discuss blood test result") ? "Remind us why you required a blood test" : ("Tell us why you would like " + category)} </h2>


                <div>
            <p> A brief description of the problem </p>
            <input ref={problemRef} type="text" size={50} placeholder='eg. "Persistent back-ache"'/>
            </div>



            <div>
            <p>Severity of symptoms on a scale from 1 (least severe) to 10 (most severe)  </p>
            <input ref={symptomsRef} type="text" size={50} placeholder='eg. "8"'/>
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

