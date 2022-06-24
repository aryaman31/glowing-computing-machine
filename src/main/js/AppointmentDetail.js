import React from 'react'

export default function AppointmentDetail({ appoint }) {
  return (
      <div className='Appointment'>

        <div>
          <h3>Appointment time:</h3>
          <p>{appoint.time}</p>
        </div>

        <div>
          <h3>Doctor:</h3>
          <p>{appoint.doctor}</p>
        </div>

        <div>
          <h3>Problem faced:</h3>
          <p>{appoint.problem}</p>
        </div>
        
        <div>
          <h3>Description:</h3>
          {appoint.description.split('\n').map(str => <p id={appoint.id}>{str}</p>)}
        </div>
        
      </div>
  )
}
