import React from 'react'

export default function Slot({ startTime, endTime, hasSelected }) {

  const options = {  weekday: 'long', month: 'long', day: 'numeric' }
  const printDate = new Date(startTime).toLocaleString('en-GB', options)

  const options2 = { hour: 'numeric', minute: 'numeric' }
  const printStartTime = new Date(startTime).toLocaleString('en-GB', options2)
  const printEndTime = new Date(endTime).toLocaleString('en-GB', options2)  

  const printSlot = (printDate, printStartTime, printEndTime, hasSelected) => {
    if (hasSelected) {
      return (printDate + ' ' + printStartTime + '-' + printEndTime) 
    }
  }

  return (
    <>
      <p>Slot selected: </p>
      <div>{printSlot(printDate, printStartTime, printEndTime, hasSelected)}</div>
    </>
  )
}
