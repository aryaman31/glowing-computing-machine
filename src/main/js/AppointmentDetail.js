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
          <p>{appoint.description.split('\n').map(str => <p>{str}</p>)}</p>
        </div>
        
      </div>
  )
}
