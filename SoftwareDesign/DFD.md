# Data Flow Diagrams (DFD)

## Introduction
A Data Flow Diagram (DFD) is a graphical representation of how data flows through an information system. It shows the processes, data stores, external entities, and the data flows between them.

## Basic Elements of DFD

1. **Process** (Circle/Bubble) - Transforms input data into output
2. **Data Flow** (Arrow) - Shows data movement
3. **Data Store** (Two parallel lines) - Where data is stored
4. **External Entity** (Rectangle) - Source or destination of data

## Levels of DFD

### Context Level (Level 0)
Shows the system's interaction with external entities.

```mermaid
graph LR
    Customer((Customer)) -->|Order Details| OrderSystem((Order System))
    OrderSystem -->|Order Confirmation| Customer
    Supplier((Supplier)) -->|Stock Updates| OrderSystem
    OrderSystem -->|Purchase Orders| Supplier
```

### Level 1 DFD
Breaks down the main process into sub-processes.

```mermaid
graph LR
    Customer((Customer)) -->|Order| Process1[Process Order]
    Process1 -->|Validated Order| Process2[Check Inventory]
    Process2 -->|Stock Request| DS1[(Inventory DB)]
    DS1 -->|Stock Status| Process2
    Process2 -->|Order Status| Process3[Generate Invoice]
    Process3 -->|Invoice| Customer
```

## Real-World Example: Online Shopping System

### Context Level

```mermaid
graph LR
    Customer((Customer)) -->|Browse/Order| OS((Online Shop))
    Admin((Admin)) -->|Manage Products| OS
    OS -->|Order Confirmation| Customer
    OS -->|Reports| Admin
```

### Level 1: Detailed Process Flow

```mermaid
graph TB
    Customer((Customer)) -->|Login Details| P1[Authentication]
    P1 -->|User Data| DS1[(User DB)]
    Customer -->|Browse Items| P2[Product Catalog]
    P2 -->|Product Info| DS2[(Product DB)]
    Customer -->|Place Order| P3[Order Processing]
    P3 -->|Order Details| DS3[(Order DB)]
    P3 -->|Stock Check| DS2
    P3 -->|Payment Request| P4[Payment Processing]
    P4 -->|Transaction| DS4[(Payment DB)]
    P4 -->|Confirmation| Customer
```

## Additional Examples and DFD Levels

### Hospital Management System

#### Context Diagram (Level 0)

```mermaid
graph LR
    Patient((Patient)) -->|Patient Information| HMS((Hospital Management System))
    Doctor((Doctor)) -->|Treatment Details| HMS
    HMS -->|Appointment Details| Patient
    HMS -->|Patient History| Doctor
    Insurance((Insurance Company)) -->|Coverage Information| HMS
    HMS -->|Claims| Insurance
    Pharmacy((Pharmacy)) -->|Medicine Availability| HMS
    HMS -->|Prescriptions| Pharmacy
```

#### Level 1 DFD: Hospital Management System

```mermaid
graph TB
    Patient((Patient)) -->|Registration Info| P1[Patient Registration]
    P1 -->|Patient Data| DS1[(Patient Database)]
    
    Patient -->|Symptoms| P2[Appointment Scheduling]
    Doctor((Doctor)) -->|Availability| P2
    P2 -->|Schedule Data| DS2[(Appointment DB)]
    
    P2 -->|Appointment Details| P3[Medical Consultation]
    DS1 -->|Patient History| P3
    P3 -->|Diagnosis & Treatment| P4[Prescription Management]
    
    P4 -->|Medication Details| DS3[(Prescription DB)]
    P4 -->|Prescription| Pharmacy((Pharmacy))
    
    P3 -->|Treatment Details| P5[Billing]
    DS1 -->|Patient Insurance| P5
    P5 -->|Bill| Patient
    P5 -->|Claim| Insurance((Insurance))
```

#### Level 2 DFD: Zoom into Patient Registration Process

```mermaid
graph TB
    Patient((Patient)) -->|Personal Details| P1.1[Collect Patient Info]
    P1.1 -->|Raw Data| P1.2[Validate Information]
    P1.2 -->|Valid Data| P1.3[Assign Patient ID]
    P1.3 -->|New Patient Record| DS1[(Patient Database)]
    
    Patient -->|Insurance Card| P1.4[Insurance Verification]
    P1.4 -->|Policy Number| External[Insurance API]
    External -->|Coverage Details| P1.4
    P1.4 -->|Verified Insurance| DS1
    
    DS1 -->|Existing Records| P1.5[Update Patient Record]
    P1.5 -->|Updated Record| DS1
    P1.3 -->|Patient ID| Patient
```

### Library Management System

#### Context Diagram (Level 0)

```mermaid
graph LR
    Member((Member)) -->|Book Request| LMS((Library Management System))
    LMS -->|Books| Member
    Librarian((Librarian)) -->|Catalog Updates| LMS
    LMS -->|Reports| Librarian
    Supplier((Book Supplier)) -->|New Books| LMS
    LMS -->|Purchase Orders| Supplier
```

#### Level 1 DFD: Library Management System

```mermaid
graph TB
    Member((Member)) -->|Registration| P1[Member Management]
    P1 -->|Member Data| DS1[(Member DB)]
    
    Member -->|Search Query| P2[Book Search]
    P2 -->|Book Info Request| DS2[(Book Catalog)]
    DS2 -->|Book Details| P2
    P2 -->|Search Results| Member
    
    Member -->|Checkout Request| P3[Book Circulation]
    P3 -->|Availability Check| DS2
    P3 -->|Borrow Records| DS3[(Circulation DB)]
    P3 -->|Book| Member
    
    Member -->|Return Book| P4[Return Processing]
    P4 -->|Update Stock| DS2
    P4 -->|Update Records| DS3
    
    Librarian((Librarian)) -->|New Book Data| P5[Catalog Management]
    P5 -->|Update Catalog| DS2
    
    Librarian -->|Order Request| P6[Acquisition]
    P6 -->|Orders| Supplier((Supplier))
    Supplier -->|Book Delivery| P6
    P6 -->|New Inventory| P5
```

## Advanced DFD Concepts

### Physical vs. Logical DFDs

**Logical DFDs** focus on the business events and functions without specifying physical implementation:

```mermaid
graph LR
    Customer -->|Order Details| Process[Process Order]
    Process -->|Order Confirmation| Customer
```

**Physical DFDs** show how the system is actually implemented:

```mermaid
graph LR
    Customer -->|Web Form Input| WebServer[Web Server]
    WebServer -->|API Call| OrderService[Order Service]
    OrderService -->|SQL Query| Database[(Order Database)]
    OrderService -->|JSON Response| WebServer
    WebServer -->|HTML Page| Customer
```

### Gane & Sarson vs. Yourdon & Coad Notation

**Gane & Sarson Notation** uses squares with rounded corners for processes:

```mermaid
graph LR
    Customer((Customer)) -->|Order| Process["Process Order
    1.0"]
    Process -->|Confirmation| Customer
```

**Yourdon & Coad Notation** uses circles for processes:

```mermaid
graph LR
    Customer((Customer)) -->|Order| Process((Process Order))
    Process -->|Confirmation| Customer
```

## DFD Balancing

Balancing ensures that data flowing into a decomposed process equals data flowing out:

### Level 0
```mermaid
graph LR
    E1((Customer)) -->|A| P0((System))
    P0 -->|B| E2((Supplier))
    E2 -->|C| P0
    P0 -->|D| E1
```

### Level 1 (Balanced)
```mermaid
graph LR
    E1((Customer)) -->|A| P1[Process 1]
    P1 -->|X| P2[Process 2]
    P2 -->|Y| P3[Process 3]
    P3 -->|D| E1
    E2((Supplier)) -->|C| P2
    P2 -->|B| E2
```

## Best Practices

1. **Numbering Convention**: Use decimal numbering for processes (1.0, 1.1, 1.2)
2. **Balancing**: Ensure data flows are consistent between levels
3. **Naming**: Use clear, specific names for processes and data flows
4. **Limit Processes**: Keep 7±2 processes per diagram for readability

## Common Mistakes to Avoid

- Don't show data flow between external entities
- Don't skip levels in decomposition
- Don't forget to label all data flows
- Don't show control flows (only data flows)

## Tools for Creating DFDs

- Draw.io
- Lucidchart
- Visual Paradigm
- Mermaid (as shown in examples)

## DFD Analysis Techniques

### Step-by-Step DFD Creation Process

1. **Identify External Entities**: Determine who/what provides or receives data
2. **Identify Processes**: Define transformations occurring on the data
3. **Identify Data Stores**: Determine where data is stored
4. **Identify Data Flows**: Connect entities, processes, and stores
5. **Create Context Diagram**: Show system boundaries
6. **Create Level 1 DFD**: Decompose main processes
7. **Create Lower Level DFDs**: Further decompose complex processes
8. **Verify Balance**: Ensure data consistency between levels

### Using DFDs in Agile Development

DFDs remain valuable in Agile environments:

- Create high-level DFDs during initial sprint planning
- Refine lower-level DFDs during sprint iterations
- Use DFDs to communicate with stakeholders about data requirements
- Update DFDs as system understanding evolves

### Example: Banking System with Level 0, 1, and 2

#### Level 0 (Context)
```mermaid
graph LR
    Customer((Customer)) -->|Transaction Request| BankingSystem((Banking System))
    BankingSystem -->|Transaction Result| Customer
    Bank((Partner Bank)) -->|Account Information| BankingSystem
    BankingSystem -->|Transfer Requests| Bank
    Admin((Admin)) -->|System Config| BankingSystem
    BankingSystem -->|Reports| Admin
```

#### Level 1 DFD
```mermaid
graph TB
    Customer((Customer)) -->|Login Credentials| P1[Authentication]
    P1 -->|User Data Query| DS1[(User Database)]
    DS1 -->|User Profile| P1
    P1 -->|Access Token| Customer
    
    Customer -->|Transaction Request| P2[Transaction Processing]
    P2 -->|Transaction Details| DS2[(Transaction DB)]
    P2 -->|Account Query| DS3[(Account DB)]
    DS3 -->|Balance Info| P2
    
    P2 -->|External Transfer| P3[Inter-Bank Transfer]
    P3 -->|Transfer Request| Bank((Partner Bank))
    Bank -->|Transfer Confirmation| P3
    P3 -->|Update Record| DS2
    
    Admin((Admin)) -->|Report Request| P4[Reporting]
    DS2 -->|Transaction Data| P4
    DS3 -->|Account Data| P4
    P4 -->|Generated Reports| Admin
    
    Admin -->|Config Changes| P5[System Configuration]
    P5 -->|Settings| DS4[(Config DB)]
```

#### Level 2 DFD: Transaction Processing Detail
```mermaid
graph TB
    Customer((Customer)) -->|Transaction Details| P2.1[Validate Transaction]
    P2.1 -->|Account Number| DS3[(Account DB)]
    DS3 -->|Account Status| P2.1
    
    P2.1 -->|Valid Transaction| P2.2[Verify Funds]
    P2.2 -->|Balance Query| DS3
    DS3 -->|Available Balance| P2.2
    
    P2.2 -->|Sufficient Funds| P2.3[Execute Transaction]
    P2.3 -->|Debit Account| DS3
    P2.3 -->|Credit Account| DS3
    
    P2.3 -->|Transaction Record| P2.4[Generate Receipt]
    P2.4 -->|Transaction Log| DS2[(Transaction DB)]
    P2.4 -->|Receipt| Customer
    
    P2.3 -->|Notification Data| P2.5[Send Notification]
    DS3 -->|Contact Info| P2.5
    P2.5 -->|SMS/Email| Customer
```

## Conclusion

Data Flow Diagrams are powerful tools for visualizing how information moves through a system. By properly leveling DFDs from context diagrams to detailed process specifications, software engineers can effectively communicate system requirements and design with both technical and non-technical stakeholders.
