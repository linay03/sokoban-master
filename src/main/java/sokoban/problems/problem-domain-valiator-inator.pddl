(define (problem Sokoban_generated)
  (:domain Sokoban)

  (:objects
    b1 - box
    pos_0_0 pos_0_1 pos_0_2 pos_1_0 pos_1_1 pos_1_2 pos_2_0 pos_2_1 pos_2_2 - position
    U D L R - direction
  )
    ; 0_0       | 1_0     | 2_0
    ; 0_1 guard | 1_1 box | 2_1 dest
    ; 0_2       | 1_2     | 2_2
  (:init
    (guard-at pos_0_1) ; player is at location pos_1_0
    (box-at pos_1_1) ; box b1 is at location pos_1_1
    (adjacent pos_0_0 pos_0_1 D) (adjacent pos_0_1 pos_0_0 U)
    (adjacent pos_0_1 pos_0_2 D) (adjacent pos_0_2 pos_0_1 U)
    (adjacent pos_0_0 pos_1_0 R) (adjacent pos_1_0 pos_0_0 L)
    (adjacent pos_0_1 pos_1_1 R) (adjacent pos_1_1 pos_0_1 L)
    (adjacent pos_0_2 pos_1_2 R) (adjacent pos_1_2 pos_0_2 L)
    (adjacent pos_1_0 pos_1_1 D) (adjacent pos_1_1 pos_1_0 U)
    (adjacent pos_1_1 pos_1_2 D) (adjacent pos_1_2 pos_1_1 U)
    (adjacent pos_1_0 pos_2_0 R) (adjacent pos_2_0 pos_1_0 L)
    (adjacent pos_1_1 pos_2_1 R) (adjacent pos_2_1 pos_1_1 L)
    (adjacent pos_1_2 pos_2_2 R) (adjacent pos_2_2 pos_1_2 L)

    (clear pos_0_0) (clear pos_0_2) (clear pos_1_0) (clear pos_1_2) (clear pos_2_0) (clear pos_2_1) (clear pos_2_2)
    )

    (:goal 
    (box-at pos_2_1) ; box b1 is at location
    )

)