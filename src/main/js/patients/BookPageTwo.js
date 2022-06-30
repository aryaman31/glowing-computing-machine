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

            <div className='card'>
            {/* type of appointment*/}
            <div>
            <p> <b>Please select the type of appointment you would like: </b></p>

            <Select
              options={appointmentCategories}
              onChange={(e) => {handleCategory(e)}} 
              placeholder="Choose a category from the dropdown below"
            />
            
            </div>


            {/* way of seeing doctor */}

            <p><b>Would you like an in-person appointment, telephone appointment or home appointment? </b></p>
            <p><i>Please note, due to high demand for in-person appointments, certain appointments will be required to be taken over the phone.</i> </p>

            <Select
              options={appTypeOptions}
              onChange={(e) => {setAppointmentType(e.value)}}
              isOptionDisabled={(option) => option.disabled} 
              placeholder="Choose your preferred way of seeing a doctor from the dropdown below"
            />
            </div>


            {/* enter details about problem*/}

            <div className='card '>
            <h2>{(category === "discuss blood test result") ? "Remind us why you required a blood test" : ("Tell us why you would like " + category)} </h2>


                <div>
            <p> <b>A brief description of the problem </b> </p>
            <input ref={problemRef} type="text" size={50} placeholder='eg. "Persistent back-ache"' className='wide-text-field'/>
            </div>



            <div>
            <p> <b>Severity of symptoms on a scale from 1 (least severe) to 10 (most severe)  </b></p>
            <input ref={symptomsRef} type="text" size={50} placeholder='eg. "8"' className='wide-text-field'/>
            </div>

            <div>
            <p> <b> Duration of symptoms </b></p>
            <input ref={durationRef} type="text" size={50} required placeholder='eg. "3 weeks"' className='wide-text-field'/>
            </div>

            <div>
            <p> <b>(Optional) Anything else the doctor might like to know? </b></p>
            <textarea
                ref={extraInfoRef}
                rows={5}
                cols={50}
                placeholder='eg. "Experiencing strong pain in upper-back for 4 weeks"' className='wide-text-field'></textarea>
            </div>
            </div>

            <div className='row'>
                <Link to="/home" onClick={handleConfirmation} className='btn'><b>Cancel</b></Link>

                <Link to="/appointment_slot" onClick={handleConfirmation} className='btn'><b>Next</b></Link>
            </div>

            <h3> Step 2 of 3</h3>

        </>
    )

}

