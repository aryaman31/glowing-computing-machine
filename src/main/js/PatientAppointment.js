import React from 'react'

export default function PatientAppointment({ appoint }) {
  return (
    <>
    <p>{appoint.time}</p>
    <p>Patient name: {appoint.name}</p>
    <p>Problem faced: {appoint.problem}</p>
    <p>Description:</p>
    {appoint.description.split('\n').map(str => <p>{str}</p>)}

    </>
  )
}
