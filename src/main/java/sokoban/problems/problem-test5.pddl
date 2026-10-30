(define (problem problem-test5)
  (:domain Sokoban)
  (:objects
    box0 - box
    box1 - box
    box2 - box
    U D L R - direction
    pos_1_1 - position
    pos_2_1 - position
    pos_3_1 - position
    pos_4_1 - position
    pos_5_1 - position
    pos_1_2 - position
    pos_3_2 - position
    pos_4_2 - position
    pos_5_2 - position
    pos_1_3 - position
    pos_2_3 - position
    pos_3_3 - position
    pos_5_3 - position
    pos_2_4 - position
    pos_3_4 - position
    pos_4_4 - position
    pos_5_4 - position
    pos_0_5 - position
    pos_2_5 - position
    pos_3_5 - position
    pos_0_6 - position
    pos_2_6 - position
    pos_3_6 - position
    pos_5_6 - position
    pos_6_6 - position
    pos_0_7 - position
    pos_5_7 - position
    pos_6_7 - position
  )
  (:init
    (guard-at pos_4_4)
    (box-at pos_3_2)
    (box-at pos_2_3)
    (box-at pos_3_4)
    (clear pos_1_1)
    (adjacent pos_1_1 pos_1_2 D)
    (adjacent pos_1_1 pos_2_1 R)
    (clear pos_2_1)
    (adjacent pos_2_1 pos_1_1 L)
    (adjacent pos_2_1 pos_3_1 R)
    (clear pos_3_1)
    (adjacent pos_3_1 pos_3_2 D)
    (adjacent pos_3_1 pos_2_1 L)
    (adjacent pos_3_1 pos_4_1 R)
    (clear pos_4_1)
    (adjacent pos_4_1 pos_4_2 D)
    (adjacent pos_4_1 pos_3_1 L)
    (adjacent pos_4_1 pos_5_1 R)
    (clear pos_5_1)
    (adjacent pos_5_1 pos_5_2 D)
    (adjacent pos_5_1 pos_4_1 L)
    (clear pos_1_2)
    (adjacent pos_1_2 pos_1_1 U)
    (adjacent pos_1_2 pos_1_3 D)
    (adjacent pos_3_2 pos_3_1 U)
    (adjacent pos_3_2 pos_3_3 D)
    (adjacent pos_3_2 pos_4_2 R)
    (clear pos_4_2)
    (adjacent pos_4_2 pos_4_1 U)
    (adjacent pos_4_2 pos_3_2 L)
    (adjacent pos_4_2 pos_5_2 R)
    (clear pos_5_2)
    (adjacent pos_5_2 pos_5_1 U)
    (adjacent pos_5_2 pos_5_3 D)
    (adjacent pos_5_2 pos_4_2 L)
    (clear pos_1_3)
    (adjacent pos_1_3 pos_1_2 U)
    (adjacent pos_1_3 pos_2_3 R)
    (adjacent pos_2_3 pos_2_4 D)
    (adjacent pos_2_3 pos_1_3 L)
    (adjacent pos_2_3 pos_3_3 R)
    (clear pos_3_3)
    (adjacent pos_3_3 pos_3_2 U)
    (adjacent pos_3_3 pos_3_4 D)
    (adjacent pos_3_3 pos_2_3 L)
    (clear pos_5_3)
    (adjacent pos_5_3 pos_5_2 U)
    (adjacent pos_5_3 pos_5_4 D)
    (clear pos_2_4)
    (adjacent pos_2_4 pos_2_3 U)
    (adjacent pos_2_4 pos_2_5 D)
    (adjacent pos_2_4 pos_3_4 R)
    (adjacent pos_3_4 pos_3_3 U)
    (adjacent pos_3_4 pos_3_5 D)
    (adjacent pos_3_4 pos_2_4 L)
    (adjacent pos_3_4 pos_4_4 R)
    (adjacent pos_4_4 pos_3_4 L)
    (adjacent pos_4_4 pos_5_4 R)
    (clear pos_5_4)
    (adjacent pos_5_4 pos_5_3 U)
    (adjacent pos_5_4 pos_4_4 L)
    (clear pos_0_5)
    (adjacent pos_0_5 pos_0_6 D)
    (clear pos_2_5)
    (adjacent pos_2_5 pos_2_4 U)
    (adjacent pos_2_5 pos_2_6 D)
    (adjacent pos_2_5 pos_3_5 R)
    (clear pos_3_5)
    (adjacent pos_3_5 pos_3_4 U)
    (adjacent pos_3_5 pos_3_6 D)
    (adjacent pos_3_5 pos_2_5 L)
    (clear pos_0_6)
    (adjacent pos_0_6 pos_0_5 U)
    (adjacent pos_0_6 pos_0_7 D)
    (clear pos_2_6)
    (adjacent pos_2_6 pos_2_5 U)
    (adjacent pos_2_6 pos_3_6 R)
    (clear pos_3_6)
    (adjacent pos_3_6 pos_3_5 U)
    (adjacent pos_3_6 pos_2_6 L)
    (clear pos_5_6)
    (adjacent pos_5_6 pos_5_7 D)
    (adjacent pos_5_6 pos_6_6 R)
    (clear pos_6_6)
    (adjacent pos_6_6 pos_6_7 D)
    (adjacent pos_6_6 pos_5_6 L)
    (clear pos_0_7)
    (adjacent pos_0_7 pos_0_6 U)
    (clear pos_5_7)
    (adjacent pos_5_7 pos_5_6 U)
    (adjacent pos_5_7 pos_6_7 R)
    (clear pos_6_7)
    (adjacent pos_6_7 pos_6_6 U)
    (adjacent pos_6_7 pos_5_7 L)
  )
  (:goal
    (and
      (box-at pos_2_1)
      (box-at pos_3_3)
      (box-at pos_2_4)
    )
  )
)
