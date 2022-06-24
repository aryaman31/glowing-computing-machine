import Appointment from './Appointment'
import Dialog from './Dialog.jsx'
import {useState, useRef, Fragment } from 'react'


export default function AppointmentList({ upcomings, setUpcoming, allAppoints, setAllAppoints }) {

  const [dialog, setDialog] = useState({
    message:'',
    isLoading:false,
  })

  const idProductRef = useRef()

  const handleDialog = (message, isLoading) => {
    setDialog({
      message,
      isLoading,
    })
  }

  function handleCancel(k) {
    handleDialog('Are you sure you want to cancel this appointment?', true)
    idProductRef.current = k
    // const temp = [...upcomings]
    // setUpcoming(temp.filter(elem => elem.id !== k))
  }

  const areUSureDelete = (choose) => {
    if (choose) {
      const temp = [...upcomings]
      setUpcoming(temp.filter(elem => elem.id !== idProductRef.current))
      const temp2 = [...allAppoints]
      setAllAppoints(temp2.filter(elem => elem.id !== idProductRef.current))
      handleDialog('', false)
    } else {
      handleDialog('', false)
    }
  }

  return (
    upcomings.map(a => {
        return (
          <Fragment key={a.id}>
            <Appointment appointment={a}/>

            <button onClick={() => handleCancel(a.id)} className='btn small-width'>
              Cancel appointment
            </button>

            { dialog.isLoading && <Dialog onDialog={areUSureDelete} message={dialog.message}/>}
            <hr/>
          </Fragment>
        )
    })
  )
}
